package config

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/sqlite"
)

//var Once sync.Once
var DB *gorm.DB

func InitDB() error {
	return InitTestDB()
}

func InitTestDB() error {
	//Once.Do(InitFlags)
	if DB != nil {
		err := DB.Close()
		if err != nil {
			return err
		}
	}
	db, err := gorm.Open("sqlite3", ":memory:")
	if err != nil {
		return err
	}
	DB = db
	return nil
}
