package controllers

import javax.inject.Inject

import anorm._
import play.api.Play.current
import play.api.libs.json._
import play.api.mvc._
import play.api.db._

class Application @Inject()(db:Database) extends Controller {
    
  def dummy = Action {
    Ok(Json.parse("""{"foo":"bar"}"""))    
  }

  def test = Action {
    db.withConnection { implicit conn =>
        var result =
        SQL(
        """
            INSERT INTO json_store(id,value)
            VALUES ({id},{json_data})
        """
        ).on("id" -> "101", "json_data" -> """{"foo":"bar"}""").execute()
    }
    
    Ok(Json.parse("""{"message":"ok"}"""))      
  }

  def find(id: String) = Action {
    var result = List[String]()
    
    db.withConnection { implicit conn =>
        result =
        SQL(
        """
            SELECT value
            FROM json_store
            WHERE id = {id} 
        """
        ).on("id" -> id).as(SqlParser.scalar[String].*)
    }
    
    if (!result.isEmpty) {
        Ok(Json.parse(result.head)) 
    } else {
        Ok(Json.parse("""{"message":"no records found for id"}"""))
    }
  }
/*
  def find = Action(BodyParsers.parse.json) { request =>
    val b = request.body.validate[Book]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      book => {
        addBook(book)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
*/
}
