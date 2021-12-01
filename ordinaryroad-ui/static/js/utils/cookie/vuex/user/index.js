import Cookies from 'js-cookie'

export const REMEMBER_ME_KEY = 'rememberMe'
export const TOKEN_INFO_KEY = 'tokenInfo'
export const USER_INFO_KEY = 'userInfo'

export function getRememberMe () {
  return Cookies.get(REMEMBER_ME_KEY) === 'true'
}

export function setRememberMe (value) {
  Cookies.set(REMEMBER_ME_KEY, value, { expires: 365 })
}

export function getTokenInfo () {
  const fromCookie = Cookies.get(TOKEN_INFO_KEY)
  return fromCookie ? JSON.parse(fromCookie) : undefined
}

export function setTokenInfo (tokenInfo) {
  Cookies.set(TOKEN_INFO_KEY, JSON.stringify(tokenInfo), {
    expires: getRememberMe() ? 30 : null
  })
}

export function removeTokenInfo () {
  Cookies.remove(TOKEN_INFO_KEY)
}

export function setUserInfo (tokenInfo) {
  Cookies.set(USER_INFO_KEY, JSON.stringify(tokenInfo), {
    expires: getRememberMe() ? 30 : null
  })
}

export function removeUserInfo () {
  Cookies.remove(USER_INFO_KEY)
}