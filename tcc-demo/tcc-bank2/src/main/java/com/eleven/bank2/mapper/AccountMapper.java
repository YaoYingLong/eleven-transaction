package com.eleven.bank2.mapper;

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
    
    
    /**
     * 增加某分支事务执行记录
     * @param txNo 本地事务编号
     * @param type  1:try,2:confirm,3:cancel
     * @return
     */
    @Insert("insert into local_transaction_log values(#{txNo},#{type},now());")
    int addTransactionLog(@Param("txNo") String txNo,@Param("type") int type);
    
    /**
     * 查询分支事务是否已执行
     * @param txNo 本地事务编号
     * @param type  1:try,2:confirm,3:cancel
     * @return
     */
    @Select("select count(1) from local_transaction_log where tx_no = #{txNo} and type=#{type} ")
    int isExistTransactionLogByType(@Param("txNo") String txNo, @Param("type") int type);
   
}
