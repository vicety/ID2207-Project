package model

import "kth.se/id2207-project/config"

func InitData() error {
	config.DB.AutoMigrate(&User{})

	var err error
	err = CreateUser(config.DB, &User{UserName: "FinancialManager", Password: "FM", Role: "FinancialManager"})
	if err != nil {
		return err
	}
	return nil
}
