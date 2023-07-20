package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/tasklist?user=crow&password=password",
    "/home/crow/code/task_list_tutorial/app/",
    "models", None, None, true, false
  )
}
