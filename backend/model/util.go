package model

import (
	"crypto/md5"
	"encoding/hex"
	"kth.se/id2207-project/config"
)

func InitData() error {
	config.DB.AutoMigrate(&User{})
	config.DB.AutoMigrate(&Form{})

	var err error
	err = CreateUser(config.DB, &User{UserName: "CustomerService", Password: "CS", Role: "CS"})
	err = CreateUser(config.DB, &User{UserName: "SeniorCustomerService", Password: "SCS", Role: "SCS"})
	err = CreateUser(config.DB, &User{UserName: "FinancialManager", Password: "FM", Role: "FM"})
	err = CreateUser(config.DB, &User{UserName: "AdministrationManager", Password: "AM", Role: "AM"})
	err = CreateUser(config.DB, &User{UserName: "SM/PM", Password: "SM/PM", Role: "SM/PM"})
	err = CreateUser(config.DB, &User{UserName: "SUB", Password: "SUB", Role: "SUB"})
	err = CreateUser(config.DB, &User{UserName: "HR", Password: "HR", Role: "HR"})
	if err != nil {
		return err
	}

	err = CreateForm(config.DB, &Form{
		FormId:          "123",
		FormType:        "EVENT",
		ResponsibleUser: "CustomerService",
		ClientName:      "xxx",
		From:            "2020-12-12",
		To:              "2021-09-09",
		Number:          "1",
		Prefer:          "DRINK",
		ExpectedBudget:  "3",
		Status:          "AD",
		Timestamp:       0,
	})
	if err != nil {
		return err
	}

	return nil
}

func Md5(s string) string {
	data := []byte(s)
	m5 := md5.Sum(data)
	return hex.EncodeToString(m5[:])
}
