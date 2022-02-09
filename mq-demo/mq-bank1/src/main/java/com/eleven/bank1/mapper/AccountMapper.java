package com.eleven.bank1.mapper;

import com.eleven.bank1.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {
    
    @Update("update t_account set account_balance=account_balance - #{amount} where account_balance>=#{amount} and account_no=#{accountNo} ")
    int subtractAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);
    
    @Update("update t_account set account_balance=account_balance + #{amount} where account_no=#{accountNo} ")
    int addAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);
    
    
    @Select("select * from t_account where where account_no=#{accountNo}")
    Account findByIdAccountNo(@Param("accountNo") String accountNo);
    
    
    
    @Select("select count(1) from local_transaction_log where tx_no = #{txNo}")
    int isExistTx(String txNo);
    
    
    @Insert("insert into local_transaction_log values(#{txNo},now());")
    int addTx(String txNo);
}
