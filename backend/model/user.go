package model

import "github.com/jinzhu/gorm"

type User struct {
	UserName string `json:"userName"`
	Password string `json:"password"`
	Role     string `json:"role"`
}

func (User) TableName() string {
	return "user"
}

func CreateUser(db *gorm.DB, user *User) error {
	return db.Create(user).Error
}

func GetUser(db *gorm.DB, userId string) (*User, error) {
	user := &User{}
	result := db.Model(&User{}).Where("user_name = ?", userId).First(user)
	return user, result.Error
}
