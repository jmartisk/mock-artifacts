mutation createFrunubucator {
  create(person:
    {
      name: "Frunubucator",
      gender: OTHER
    }
  ){
    gender
    name
  }
}