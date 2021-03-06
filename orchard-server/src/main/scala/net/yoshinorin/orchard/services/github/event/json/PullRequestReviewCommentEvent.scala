package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, PullRequestReviewCommentEvents}
import net.yoshinorin.orchard.utils.Logger

class PullRequestReviewCommentEvent(event: Events, json: Json) extends JsonBase[PullRequestReviewCommentEvents] with EventBase with Logger {

  val pullRequestReviewCommentEvent: Option[PullRequestReviewCommentEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass[PullRequestReviewCommentEvents]: Option[PullRequestReviewCommentEvents] = this.pullRequestReviewCommentEvent.asInstanceOf[Option[PullRequestReviewCommentEvents]]

  /**
   * Insert to DataBase
   *
   * @tparam PullRequestReviewCommentEvents
   */
  override def insert[PullRequestReviewCommentEvents]: Unit = this.pullRequestReviewCommentEvent.map { _.insert }

  /**
   * Convert JSON to PullRequestReviewCommentEvents case class
   *
   * @return
   */
  override def convert: Option[PullRequestReviewCommentEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val pullRequestNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && pullRequestNumber.isRight) {
      Some(PullRequestReviewCommentEvents(event.id, event.userName, event.repositoryId, pullRequestNumber.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to PullRequestReviewCommentEvents.")
      None
    }
  }
}

object PullRequestReviewCommentEvent {

  def apply(event: Events, json: Json): PullRequestReviewCommentEvent = new PullRequestReviewCommentEvent(event, json)

}
