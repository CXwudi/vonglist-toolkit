package mikufan.cx.vtool.shared.model.util

class VocaloidException : Exception {

  constructor(message: String) : super(message)
  constructor(message: String, cause: Throwable) : super(message, cause)
  constructor(cause: Throwable) : super(cause)
  constructor() : super()
}