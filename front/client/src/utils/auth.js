import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token'
const ActivateKey = 'activate'
const Phone = 'phone'
const Name = 'name'
const Role = 'role'
const ShoppingCar = 'shoppingCar'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setActivate(activate) {
  return Cookies.set(ActivateKey, activate)
}

export function getActivate() {
  return Cookies.get(ActivateKey)
}

export function getPhone() {
  return Cookies.get(Phone)
}

export function setPhone(phone) {
  return Cookies.set(Phone, phone)
}

export function removePhone() {
  return Cookies.remove(Phone)
}

export function getName() {
  return Cookies.get(Name)
}

export function setName(name) {
  return Cookies.set(Name, name)
}

export function setRole(role) {
  return Cookies.set(Role, role)
}

export function getRole() {
  return Cookies.get(Role)
}

export function getShoppingCar() {
  return Cookies.get(Role)
}

export function setShoppingCar(shoppingCar) {
  return Cookies.set(ShoppingCar, shoppingCar)
}

export function deleteShoppingCar(shoppingCar) {
  return Cookies.remove(ShoppingCar)
}
