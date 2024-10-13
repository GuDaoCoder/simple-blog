const TOKEN_KEY = 'Authorization'

const isLogin = () => {
  return !!localStorage.getItem(TOKEN_KEY)
}

const getToken = (): string => {
  const token = localStorage.getItem(TOKEN_KEY)
  return token ? token.toString() : ''
}

const setToken = (token: string) => {
  localStorage.setItem(TOKEN_KEY, token)
}

const clearToken = () => {
  localStorage.removeItem(TOKEN_KEY)
}

export { isLogin, getToken, setToken, clearToken }
