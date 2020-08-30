import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token'
const ActivateKey = 'activate'
const Phone = 'phone'
const Name = 'name'
const Role = 'role'

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
