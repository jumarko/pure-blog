# pure-blog

My own blog app implementation for the purpose of PurelyFunctional.tv exercise.
See https://purelyfunctional.tv/functional-programming-career-guide/10-side-projects-resume/
for more examples of good side projects.

Project's github page: https://github.com/jumarko/pure-blog

## Deployment

Application is currently available at http://pureblog.curiousprogrammer.net:3000/

[Welcome blog post](http://pureblog.curiousprogrammer.net:3000/posts/3) contain some details
and future ideas.

### To deploy a new version:

```
lein uberjar
```

Then copy it to the server with `rsync`.

Right now, the DB migrations has to be handled manually.

Moreover, the hard-coded user data was inserted manually too:
```
INSERT INTO users (id, first_name, last_name, email, admin, pass) VALUES (1, 'Juraj', 'Martinka', 'jumarko@gmail.com', true, 'bcrypt+sha512$3c3da6aac4bf97a142503329e0e7fde1$12$72519f2c3e8c7be7ff5e9f7cbfe5b99f63f99279142d2e0c');
```


## Duct

### Skeleton

Skeleton of this app has been created by `lein duct` template:

```
lein new duct pure-blog +api +ataraxy +example +postgres +site
```

### Developing

#### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

#### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

#### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```

## Legal

Copyright © 2018 Juraj Martinka (curiousprogrammer.net)
