var path = require('path')
var express = require('express')

users = [("test","securePassword")]

function encrypt(key){
    return key*5;
}

function auth(user,pass){

}

app.get('/:user/:pass/:key', (req,res) => {

})