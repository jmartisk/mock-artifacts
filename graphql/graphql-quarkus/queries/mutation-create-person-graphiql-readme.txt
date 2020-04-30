mutation createFrunubucator($name: String!) {
  create(name: $name) {
    name
  }
}



QUERY VARIABLES:
{
  "name": "Frunubucator",
}