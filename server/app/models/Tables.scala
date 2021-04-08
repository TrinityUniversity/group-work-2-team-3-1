package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Relationships.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Relationships
   *  @param userId1 Database column user_id_1 SqlType(int4)
   *  @param userId2 Database column user_id_2 SqlType(int4)
   *  @param relation Database column relation SqlType(varchar), Length(40,true) */
  case class RelationshipsRow(userId1: Int, userId2: Int, relation: String)
  /** GetResult implicit for fetching RelationshipsRow objects using plain SQL queries */
  implicit def GetResultRelationshipsRow(implicit e0: GR[Int], e1: GR[String]): GR[RelationshipsRow] = GR{
    prs => import prs._
    RelationshipsRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table relationships. Objects of this class serve as prototypes for rows in queries. */
  class Relationships(_tableTag: Tag) extends profile.api.Table[RelationshipsRow](_tableTag, "relationships") {
    def * = (userId1, userId2, relation) <> (RelationshipsRow.tupled, RelationshipsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userId1), Rep.Some(userId2), Rep.Some(relation))).shaped.<>({r=>import r._; _1.map(_=> RelationshipsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_id_1 SqlType(int4) */
    val userId1: Rep[Int] = column[Int]("user_id_1")
    /** Database column user_id_2 SqlType(int4) */
    val userId2: Rep[Int] = column[Int]("user_id_2")
    /** Database column relation SqlType(varchar), Length(40,true) */
    val relation: Rep[String] = column[String]("relation", O.Length(40,varying=true))
  }
  /** Collection-like TableQuery object for table Relationships */
  lazy val Relationships = new TableQuery(tag => new Relationships(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc
   *  @param name Database column name SqlType(varchar), Length(40,true), Default(None) */
  case class UsersRow(id: Int, name: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, name) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), name)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column name SqlType(varchar), Length(40,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(40,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
