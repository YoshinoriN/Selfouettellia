CREATE TABLE pull_request_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL,
  repository_id BIGINT UNSIGNED NOT NULL,
  pull_request_number BIGINT UNSIGNED NOT NULL,
  action ENUM ('assigned','unassigned','review_requested','review_request_removed','labeled','unlabeled','opened','edited','closed','reopened'),
  created_at BIGINT UNSIGNED,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;
