package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao  implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //this method is good
    @Override
    public Transfer getTransfer(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapResultToTransfer(results);
        }
        return transfer;
    }

    //this method is good
    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            transfers.add(mapResultToTransfer(results));
        }
        return transfers;
    }

    @Override
    public int createTransfer(TransferDto transferDto) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
                + "VALUES                  (?,                ?,                    ?,              ?,          ?) "
                + "RETURNING transfer_id;";

        Long transferIdLong = jdbcTemplate.queryForObject(sql, Long.class, transferDto.getTransferTypeId(), transferDto.getTransferStatusId(), transferDto.getTransferFromId(), transferDto.getTransferToId(), transferDto.getAmount());
        int transferId = transferIdLong.intValue();
        return transferId;
    }

    //method updates the transfer_status_id to 2 (APPROVED) when approved.
    public boolean updateTransfer(Transfer transfer){
        String sql = "UPDATE transfer SET transfer_status_id = 2 WHERE transfer_id = ?;";
        int numberOfRows = jdbcTemplate.update(sql, transfer.getTransferId());
        return numberOfRows == 1;
    }


    @Override
    public List<Transfer> transfersByAccountId(int accountId) {
        String sql = "SELECT * FROM transfer WHERE (account_from = ? OR account_to = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        List<Transfer> transfers = new ArrayList<>();
            while (results.next()) {
                Transfer transfer = mapResultToTransfer(results);
                transfers.add(transfer);
            }

        return transfers;
    }

    @Override
    public List<Transfer> getPendingTransfers(int accountId) {
        String sql = "SELECT * FROM transfer WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 1";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        List<Transfer> transfers = new ArrayList<>();
        if (results.next()) {
                Transfer transfer = mapResultToTransfer(results);
                transfers.add(transfer);
            }
        return transfers;
    }

    private Transfer mapResultToTransfer(SqlRowSet results) {

        Transfer transfer = new Transfer();

        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFromId(results.getInt("account_from"));
        transfer.setAccountToId(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));

        return transfer;
    }

    @Override
    public List<Transfer> getTransferByID(int transferId) {
        List<Transfer> transfer = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, ts.transfer_status_desc, " +
                "t.account_from, tu1.username AS from_user, t.account_to, tu2.username AS to_user, t.amount " +
                "FROM transfer t " +
                "FULL JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
                "FULL JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
                "FULL JOIN account a1 ON t.account_from = a1.account_id " +
                "FULL JOIN account a2 ON t.account_to = a2.account_id " +
                "FULL JOIN tenmo_user tu1 ON a1.user_id = tu1.user_id " +
                "FULL JOIN tenmo_user tu2 ON a2.user_id = tu2.user_id " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        while(results.next()) {
            Transfer transferResult = mapResultToTransfer(results);
            transfer.add(transferResult);
        }
        return transfer;

    }
}