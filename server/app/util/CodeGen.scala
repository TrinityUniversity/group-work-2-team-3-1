package util

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/user_relationship_model?user=tom&password=password",
    // "/home/mlewis/workspaceWeb/online-quiz-and-test/server/app/", 
    "/home/tom/Code/class/webapps/group-work-2-team-3-1/server/app", 
    "models", None, None, true, false
  )
}