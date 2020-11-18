Use the default value for secretToken.maskFirstPart:

query bla {
  all {
    name,
    secretToken
  }
}

Specify a value:

query bla {
  all {
    name,
    secretToken(maskFirstPart: false)
  }
}