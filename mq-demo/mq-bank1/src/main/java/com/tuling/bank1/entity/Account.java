package com.tuling.bank1.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
	private Long id;
	private String accountName;
	private String accountNo;
	private Double accountBalance;
}