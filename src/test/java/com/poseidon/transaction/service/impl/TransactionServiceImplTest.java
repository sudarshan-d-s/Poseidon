package com.poseidon.transaction.service.impl;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {
    private final TransactionServiceImpl transactionService = new TransactionServiceImpl();
    private final TransactionDAO transactionDAO = Mockito.mock(TransactionDAO.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(transactionService, "transactionDAO", transactionDAO);
    }

    @Test
    public void listTodayTransactionsSuccess() throws TransactionException {
        when(transactionDAO.listTodaysTransactions()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.listTodaysTransactions());
    }

    @Test
    public void listTodayTransactionsFailure() throws TransactionException {
        when(transactionDAO.listTodaysTransactions())
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class, transactionService::listTodaysTransactions);
    }

    @Test
    public void saveTransactionSuccess() throws TransactionException {
        Assertions.assertNull(transactionService.saveTransaction(Mockito.mock(TransactionVO.class)));
    }

    @Test
    public void saveTransactionFailure() throws TransactionException {
        when(transactionDAO.saveTransaction(any()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.saveTransaction(Mockito.mock(TransactionVO.class)));
    }

    @Test
    public void searchTransactionsSuccess() throws TransactionException {
        when(transactionDAO.searchTransactions(any())).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.searchTransactions(Mockito.mock(TransactionVO.class)));
    }

    @Test
    public void searchTransactionsFailure() throws TransactionException {
        when(transactionDAO.searchTransactions(any()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.searchTransactions(Mockito.mock(TransactionVO.class)));
    }

    @Test
    public void fetchTransactionFromIdSuccess() throws TransactionException {
        Assertions.assertNull(transactionService.fetchTransactionFromId(1234L));
    }

    @Test
    public void fetchTransactionFromIdFailure() throws TransactionException {
        when(transactionDAO.fetchTransactionFromId(anyLong()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.fetchTransactionFromId(1234L));
    }

    @Test
    public void fetchTransactionFromTagSuccess() throws TransactionException {
        Assertions.assertNull(transactionService.fetchTransactionFromTag("tag"));
    }

    @Test
    public void fetchTransactionFromTagFailure() throws TransactionException {
        when(transactionDAO.fetchTransactionFromTag(anyString()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.fetchTransactionFromTag("tag"));
    }

    @Test
    public void updateTransactionSuccess() {
        Assertions.assertAll(() -> transactionService.updateTransaction(Mockito.mock(TransactionVO.class)));
    }

    @Test
    public void updateTransactionFailure() throws TransactionException {
        doThrow(new TransactionException(TransactionException.DATABASE_ERROR))
                .when(transactionDAO).updateTransaction(any());
        Assertions.assertAll(() -> transactionService.updateTransaction(Mockito.mock(TransactionVO.class)));
    }

    @Test
    public void deleteTransactionSuccess() {
        Assertions.assertAll(() -> transactionService.deleteTransaction(anyLong()));
    }

    @Test
    public void deleteTransactionFailure() throws TransactionException {
        doThrow(new TransactionException(TransactionException.DATABASE_ERROR))
                .when(transactionDAO).deleteTransaction(anyLong());
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.deleteTransaction(1234L));
    }

    @Test
    public void updateTransactionStatusSuccess() {
        Assertions.assertAll(() -> transactionService.updateTransactionStatus(anyLong(), anyString()));
    }

    @Test
    public void updateTransactionStatusFailure() throws TransactionException {
        doThrow(new TransactionException(TransactionException.DATABASE_ERROR))
                .when(transactionDAO).updateTransactionStatus(anyLong(), anyString());
        Assertions.assertThrows(TransactionException.class,
                () -> transactionService.updateTransactionStatus(1234L, "success"));
    }

    @Test
    public void listAllTransactionsSuccess() throws TransactionException {
        when(transactionDAO.listAllTransactions()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.listAllTransactions());
    }

    @Test
    public void listAllTransactionsFailure() throws TransactionException {
        when(transactionDAO.listAllTransactions())
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class, transactionService::listAllTransactions);
    }
}