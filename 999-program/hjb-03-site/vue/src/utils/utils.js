export const isNotNullORBlank = (...args)=> {
  for (var i = 0; i < args.length; i++) {
    var argument = args[i];
    if (argument == null || argument == '' || argument == undefined) {
      return false;
    }
  }
  return true;
}
const TokenKey = 'Admin-Token'

export function getToken() {
  return localStorage.getItem(TokenKey)
}
export function setToken(token) {
  return localStorage.setItem(TokenKey, token)
}

export function removeToken() {
  return localStorage.removeItem(TokenKey)
}
