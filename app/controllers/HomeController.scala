package controllers

import javax.inject._

import akka.util.ByteString
import play.api._
import play.api.http.HttpEntity
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController extends Controller {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action {
    Ok("Enter username & password")
  }


  def login(username: String, password: String) = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.requestSession()).withSession("username" -> s"$username", "password" -> s"$password")
  }

  def requestSession() = Action{ implicit request: Request[AnyContent] =>
     val password = request.session.get("password")
    val username = request.session.get("username")
    if(username.exists(_ == "ayush") && password.exists(_ == "1111")){
      Redirect(routes.HomeController.messageFlashing()).flashing("message" -> "Success")
    }
    else
      Redirect(routes.HomeController.messageFlashing()).flashing("message" -> "Error")
  }

  def messageFlashing() = Action { implicit request: Request[AnyContent] =>
    Ok(request.flash.get("message").get).as(HTML)
  }

}
