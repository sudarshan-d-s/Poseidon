package com.poseidon.transaction.dao.impl;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.impl.CustomerRepository;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.impl.MakeRepository;
import com.poseidon.make.dao.impl.ModelRepository;
import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.dao.entities.Transaction;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
@SuppressWarnings("unused")
@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDAOImpl.class);
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private ModelRepository modelRepository;
    @PersistenceContext
    private EntityManager em;

    /**
     * list all transactions.
     *
     * @return list of transactions
     */
    @Override
    public List<TransactionVO> listAllTransactions() throws TransactionException {
        try {
            List<Transaction> transactions = transactionRepository.findAll();
            return transactions.stream().map(this::convertToVO).collect(Collectors.toList());
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    /**
     * list today's transactions.
     */
    @Override
    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOS;
        try {
            List<Transaction> transactions = transactionRepository.todaysTransaction();
            transactionVOS = transactions.stream().map(this::convertToVO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOS;
    }

    /**
     * save transaction.
     *
     * @param currentTransaction transaction
     * @return tag number
     */
    @Override
    public String saveTransaction(final TransactionVO currentTransaction) throws TransactionException {
        try {
            Transaction txn = getTransaction(currentTransaction);
            Transaction newTxn = transactionRepository.save(txn);
            String tagNo = "WON2N" + newTxn.getTransactionId();
            newTxn.setTagno(tagNo);
            transactionRepository.save(newTxn);
            return tagNo;
        } catch (Exception ex) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    /**
     * fetch a transaction by its id.
     *
     * @param id id of the transaction
     * @return transaction
     */
    @Override
    public TransactionVO fetchTransactionFromId(final Long id) throws TransactionException {
        TransactionVO transactionVO = null;
        try {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
            if (optionalTransaction.isPresent()) {
                transactionVO = convertToVO(optionalTransaction.get());
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVO;
    }

    /**
     * update transaction.
     *
     * @param currentTransaction transaction
     */
    @Override
    public void updateTransaction(final TransactionVO currentTransaction) throws TransactionException {
        try {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(currentTransaction.getId());
            if (optionalTransaction.isPresent()) {
                Transaction txn = convertToTXN(optionalTransaction.get(), currentTransaction);
                transactionRepository.save(txn);
            }
        } catch (Exception ex) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    /**
     * delete a transaction based on id.
     *
     * @param id id of transaction
     */
    @Override
    public void deleteTransaction(final Long id) throws TransactionException {
        try {
            transactionRepository.deleteById(id);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    /**
     * fetch the transaction from its tag.
     *
     * @param tagNo tag
     * @return transaction for reporting
     */
    @Override
    public TransactionReportVO fetchTransactionFromTag(final String tagNo) throws TransactionException {
        TransactionReportVO transactionReportVO;
        try {
            Transaction transaction = transactionRepository.findBytagno(tagNo);
            transactionReportVO = convertToTransactionReportVO(transaction);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionReportVO;
    }

    /**
     * update transaction status.
     *
     * @param id     id of the transaction
     * @param status status
     */
    @Override
    public void updateTransactionStatus(final Long id, final String status) throws TransactionException {
        try {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
            if (optionalTransaction.isPresent()) {
                Transaction txn = optionalTransaction.get();
                txn.setStatus(status);
                transactionRepository.save(txn);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    /**
     * search transactions.
     *
     * @param searchTransaction transaction
     * @return list of matching transcations
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> searchTransactions(final TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = searchTxs(searchTransaction);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    private List<TransactionVO> searchTxs(final TransactionVO searchTransaction) {
        StringBuilder hqlQuery = new StringBuilder()
                .append("SELECT tr FROM Transaction tr inner join Make mk on tr.makeId=mk.id inner join Model mdl")
                .append(" on tr.modelId=mdl.id inner join Customer cust on cust.id=tr.customerId ");
        boolean hasTagNo = searchTransaction.getTagNo() != null && searchTransaction.getTagNo().trim().length() > 0;
        boolean hasCustomerName = searchTransaction.getCustomerName() != null
                && searchTransaction.getCustomerName().trim().length() > 0;
        boolean hasSerialNo = searchTransaction.getSerialNo() != null
                && searchTransaction.getSerialNo().trim().length() > 0;
        boolean hasMake = searchTransaction.getMakeId() != null && searchTransaction.getMakeId() > 0;
        boolean hasModel = searchTransaction.getModelId() != null && searchTransaction.getModelId() > 0;
        boolean hasStatus = searchTransaction.getStatus() != null && searchTransaction.getStatus().trim().length() > 0;
        boolean hasStartDateAndEndDate = searchTransaction.getStartDate() != null
                && searchTransaction.getStartDate().trim().length() > 0
                && searchTransaction.getEndDate() != null
                && searchTransaction.getEndDate().trim().length() > 0;

        if (hasTagNo || hasCustomerName || hasSerialNo || hasMake || hasModel || hasStatus || hasStartDateAndEndDate) {
            hqlQuery.append(" where");
        }

        boolean first = false;
        String tag = "";
        if (hasTagNo) {
            first = true;
            hqlQuery.append(" tr.tagno like :tag ");
            if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                tag = "%" + searchTransaction.getTagNo() + "%";
            } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                tag = searchTransaction.getTagNo() + "%";
            } else {
                tag = searchTransaction.getTagNo();
            }
        }
        String customer = "";
        if (hasCustomerName) {
            if (first) {
                hqlQuery.append(" and ");
            }
            hqlQuery.append(" cust.name like :customer ");
            if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                customer = "%" + searchTransaction.getCustomerName() + "%";
            } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                customer = searchTransaction.getCustomerName() + "%";
            } else {
                customer = searchTransaction.getCustomerName();
            }
        }
        String serial = "";
        if (hasSerialNo) {
            if (first) {
                hqlQuery.append(" and ");
            }
            hqlQuery.append(" tr.serialno like :serial ");
            if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                serial = "%" + searchTransaction.getSerialNo() + "%";
            } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                serial = searchTransaction.getSerialNo() + "%";
            } else {
                serial = searchTransaction.getSerialNo();
            }
        }
        Long make = 0L;
        if (hasMake) {
            if (first) {
                hqlQuery.append(" and ");
            }
            hqlQuery.append(" mk.id like :make ");
            make = searchTransaction.getMakeId();
        }
        Long model = 0L;
        if (hasModel) {
            if (first) {
                hqlQuery.append(" and ");
            }
            hqlQuery.append(" mdl.id like :model ");
            model = searchTransaction.getModelId();
        }
        String status = "";
        if (hasStatus) {
            if (first) {
                hqlQuery.append(" and ");
            }
            hqlQuery.append(" tr.status like :status ");
            if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                status = "%" + searchTransaction.getStatus() + "%";
            } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                status = searchTransaction.getStatus() + "%";
            } else {
                status = searchTransaction.getStatus();
            }
        }
        OffsetDateTime start = OffsetDateTime.now(ZoneId.systemDefault());
        OffsetDateTime end = OffsetDateTime.now(ZoneId.systemDefault());
        if (hasStartDateAndEndDate) {
            if (first) {
                hqlQuery.append(" and ");
            }
            start = getParsedDate(searchTransaction.getStartDate());
            end = getParsedDate(searchTransaction.getEndDate());
            hqlQuery.append(" tr.dateReported between :start and :end ");
        }
        Query query = em.createQuery(hqlQuery.toString());
        if (hasTagNo) {
            query.setParameter("tag", tag);
        }
        if (hasCustomerName) {
            query.setParameter("customer", customer);
        }
        if (hasSerialNo) {
            query.setParameter("serial", serial);
        }
        if (hasMake) {
            query.setParameter("make", make);
        }
        if (hasModel) {
            query.setParameter("model", model);
        }
        if (hasStatus) {
            query.setParameter("status", status);
        }
        if (hasStartDateAndEndDate) {
            query.setParameter("start", start);
            query.setParameter("end", end);
        }
        List<Transaction> transactions = query.getResultList();
        return transactions.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    private TransactionVO convertToVO(final Transaction txn) {
        final Optional<Customer> customer = customerRepository.findById(txn.getCustomerId());
        final Make make = makeRepository.getOne(txn.getMakeId());
        final Model model = modelRepository.getOne(txn.getModelId());
        TransactionVO transactionVO = new TransactionVO();
        transactionVO.setId(txn.getTransactionId());
        transactionVO.setTagNo(txn.getTagno());
        transactionVO.setDateReported(txn.getDateReported().toString());
        transactionVO.setProductCategory(txn.getProductCategory());
        transactionVO.setCustomerId(txn.getCustomerId());
        customer.ifPresent(value -> transactionVO.setCustomerName(value.getName()));
        transactionVO.setMakeId(txn.getMakeId());
        transactionVO.setMakeName(make.getMakeName());
        transactionVO.setModelId(txn.getModelId());
        transactionVO.setModelName(model.getModelName());
        transactionVO.setSerialNo(txn.getSerialNumber());
        transactionVO.setStatus(txn.getStatus());
        transactionVO.setAccessories(txn.getAccessories());
        transactionVO.setComplaintReported(txn.getComplaintReported());
        transactionVO.setComplaintDiagonsed(txn.getComplaintDiagnosed());
        transactionVO.setEnggRemark(txn.getEngineerRemarks());
        transactionVO.setRepairAction(txn.getRepairAction());
        transactionVO.setNotes(txn.getNote());
        return transactionVO;
    }

    private Transaction convertToTXN(final Transaction txn, final TransactionVO currentTransaction) {
        txn.setDateReported(parseDate(currentTransaction.getDateReported()));
        txn.setProductCategory(currentTransaction.getProductCategory());
        txn.setCustomerId(currentTransaction.getCustomerId());
        txn.setMakeId(currentTransaction.getMakeId());
        txn.setModelId(currentTransaction.getModelId());
        txn.setSerialNumber(currentTransaction.getSerialNo());
        txn.setAccessories(currentTransaction.getAccessories());
        txn.setComplaintReported(currentTransaction.getComplaintReported());
        txn.setComplaintDiagnosed(currentTransaction.getComplaintDiagonsed());
        txn.setEngineerRemarks(currentTransaction.getEnggRemark());
        txn.setRepairAction(currentTransaction.getRepairAction());
        txn.setNote(currentTransaction.getNotes());
        txn.setStatus(currentTransaction.getStatus());
        txn.setModifiedBy(currentTransaction.getModifiedBy());
        return txn;
    }

    private OffsetDateTime parseDate(final String dateReported) {
        OffsetDateTime parsedTime = OffsetDateTime.now(ZoneId.systemDefault());
        try {
            parsedTime = OffsetDateTime.parse(dateReported);
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
        }
        return parsedTime;
    }

    private TransactionReportVO convertToTransactionReportVO(final Transaction transaction) {
        TransactionReportVO txs = new TransactionReportVO();
        txs.setId(transaction.getTransactionId());
        txs.setTagNo(transaction.getTagno());
        txs.setDateReported(transaction.getDateReported());
        txs.setCustomerId(transaction.getCustomerId());
        Optional<Customer> customerOpt = customerRepository.findById(transaction.getCustomerId());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            txs.setCustomerName(customer.getName());
            txs.setAddress(customer.getAddress());
            txs.setPhone(customer.getPhone());
            txs.setMobile(customer.getMobile());
            txs.setEmail(customer.getEmail());
        }
        txs.setProductCategory(transaction.getProductCategory());
        Make make = makeRepository.getOne(transaction.getMakeId());
        txs.setMakeName(make.getMakeName());
        Model model = modelRepository.getOne(transaction.getModelId());
        txs.setModelName(model.getModelName());
        txs.setSerialNo(transaction.getSerialNumber());
        txs.setAccessories(transaction.getAccessories());
        txs.setComplaintReported(transaction.getComplaintReported());
        txs.setComplaintDiagnosed(transaction.getComplaintDiagnosed());
        txs.setEnggRemark(transaction.getEngineerRemarks());
        txs.setRepairAction(transaction.getRepairAction());
        txs.setNotes(transaction.getNote());
        txs.setStatus(transaction.getStatus());
        return txs;
    }

    private Transaction getTransaction(final TransactionVO currentTransaction) {
        Transaction txn = new Transaction();
        txn.setDateReported(getParsedDate(currentTransaction.getDateReported()));
        txn.setProductCategory(currentTransaction.getProductCategory());
        txn.setCustomerId(currentTransaction.getCustomerId());
        txn.setMakeId(currentTransaction.getMakeId());
        txn.setModelId(currentTransaction.getModelId());
        txn.setSerialNumber(currentTransaction.getSerialNo());
        txn.setAccessories(currentTransaction.getAccessories());
        txn.setComplaintReported(currentTransaction.getComplaintReported());
        txn.setComplaintDiagnosed(currentTransaction.getComplaintDiagonsed());
        txn.setEngineerRemarks(currentTransaction.getEnggRemark());
        txn.setRepairAction(currentTransaction.getRepairAction());
        txn.setNote(currentTransaction.getNotes());
        txn.setStatus(currentTransaction.getStatus());
        txn.setCreatedBy(currentTransaction.getCreatedBy());
        txn.setModifiedBy(currentTransaction.getModifiedBy());
        return txn;
    }

    private OffsetDateTime getParsedDate(final String dateReported) {
        OffsetDateTime reported = OffsetDateTime.now(ZoneId.systemDefault());
        try {
            reported = LocalDate.parse(dateReported, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    .atTime(OffsetTime.MIN);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return reported;
    }
}
