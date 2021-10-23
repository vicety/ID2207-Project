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

func GetUserByUserType(db *gorm.DB, userType string) (*User, error) {
	user := &User{}
	result := db.Model(&User{}).Where("role = ?", userType).First(user)
	return user, result.Error
}

func GetUserFromSid(db *gorm.DB, sId string) (*User, error) {
	var userList []User
	result := db.Model(&User{}).Find(&userList)
	if result.Error != nil {
		return nil, result.Error
	}
	for _, user := range userList {
		if Md5(user.UserName) == sId {
			return &user, nil
		}
	}
	return nil, gorm.ErrRecordNotFound
}
