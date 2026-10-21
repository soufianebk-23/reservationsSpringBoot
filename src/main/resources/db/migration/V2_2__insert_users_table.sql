INSERT INTO `users` (`id`, `login`, `password`, `firstname`, `lastname`, `email`, `langue`, `role`, `created_at`) VALUES
(1, 'bob', '$2a$12$EyDxX5wWsrJGIyo2MFIRR.LqLmmqssUdPgwasMrG7TvZmjW9ECeLW', 'Bob', 'Sull', 'bob@sull.com', 'fr', 'ADMIN', NOW()),
(2, 'anna', '$2a$12$EyDxX5wWsrJGIyo2MFIRR.LqLmmqssUdPgwasMrG7TvZmjW9ECeLW', 'Anna', 'Lyse', 'anna.lyse@sull.com', 'en', 'MEMBER', NOW());