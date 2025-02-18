package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class CompanyTermsDAOImpl implements CompanyTermsDAO {

    private static final Logger logger = LoggerFactory.getLogger(CompanyTermsDAOImpl.class);

    @Autowired
    private CompanyTermsRepository companyTermsRepository;

    /**
     * list company terms.
     *
     * @return CompanyTermsVO
     * @throws CompanyTermsException on error
     */
    @Override
    public CompanyTermsVO listCompanyTerms() throws CompanyTermsException {
        CompanyTermsVO companyTermsVO = null;
        try {
            Optional<CompanyTerms> companyTerms = companyTermsRepository.findFirstByOrderByCompanyIdAsc();
            if (companyTerms.isPresent()) {
                companyTermsVO = convertToCompanyTermsVO(companyTerms.get());
            }
        } catch (DataAccessException ex) {
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return companyTermsVO;
    }

    private CompanyTermsVO convertToCompanyTermsVO(final CompanyTerms companyTerms) {
        CompanyTermsVO companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName(companyTerms.getCompanyName());
        companyTermsVO.setCompanyAddress(companyTerms.getCompanyAddress());
        companyTermsVO.setCompanyPhoneNumber(companyTerms.getCompanyPhone());
        companyTermsVO.setCompanyEmail(companyTerms.getCompanyEmail());
        companyTermsVO.setCompanyWebsite(companyTerms.getCompanyWebsite());
        companyTermsVO.setCompanyVatTin(companyTerms.getVatTin());
        companyTermsVO.setCompanyCstTin(companyTerms.getCstTin());
        companyTermsVO.setCompanyTerms(companyTerms.getTerms());
        return companyTermsVO;
    }

    /**
     * update company details.
     *
     * @param companyTermsVO companyTermsVO
     * @return CompanyTermsVO
     * @throws CompanyTermsException on error
     */
    @Override
    public CompanyTermsVO updateCompanyDetails(final CompanyTermsVO companyTermsVO) throws CompanyTermsException {
        CompanyTermsVO termsVO = null;
        try {
            Optional<CompanyTerms> optionalCompanyTerms = companyTermsRepository.findFirstByOrderByCompanyIdAsc();
            if (optionalCompanyTerms.isPresent()) {
                CompanyTerms companyTerms = getCompanyTerms(companyTermsVO, optionalCompanyTerms.get());
                CompanyTerms updatedCompanyTerms = companyTermsRepository.save(companyTerms);
                termsVO = convertToCompanyTermsVO(updatedCompanyTerms);
            }
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage());
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return termsVO;
    }

    private CompanyTerms getCompanyTerms(final CompanyTermsVO companyTermsVO, final CompanyTerms companyTerms) {
        companyTerms.setCompanyName(companyTermsVO.getCompanyName());
        companyTerms.setTerms(companyTermsVO.getCompanyTerms());
        companyTerms.setCompanyAddress(companyTermsVO.getCompanyAddress());
        companyTerms.setCompanyPhone(companyTermsVO.getCompanyPhoneNumber());
        companyTerms.setCompanyEmail(companyTermsVO.getCompanyEmail());
        companyTerms.setCompanyWebsite(companyTermsVO.getCompanyWebsite());
        companyTerms.setVatTin(companyTermsVO.getCompanyVatTin());
        companyTerms.setCstTin(companyTermsVO.getCompanyCstTin());
        companyTerms.setModifiedBy(companyTermsVO.getModifiedBy());
        return companyTerms;
    }
}
