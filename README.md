# clj-feather

![](http://i.imgur.com/9n0uhmQ.jpg)

A leightweight clojure starter.

Clojure startup
[does not need to be slow](http://swannodette.github.io/2014/12/22/waitin/).

This uses simple shell scripts in combination with the
[maven clojure plugin](https://github.com/talios/clojure-maven-plugin) to

- start an nREPL server in combination with running custom init scripts
- run project tests

Note: This is not meant to be a replacement for a project.clj or other project
setup. Just to get a clojure runtime up *fast*. And, although this uses maven I want
to avoid a typical maven setup! I experimented to get along without maven but
for dependency management it is quite powerful. Anyway, if you want parent-poms,
archetypes and such then please move along, nothing to see here!

## Usage

After the one time preparation steps (below), change into the directory of your
clojure project and run

- `clj-feather-repl 7889` to start an nREPL server or
- `clj-feather-test` to run the project tests

`repl-init-scripts/` has some scripts that are loaded with the repl.

## TODO

- parameterize class paths
- parameterize init scripts

## Preparation

(one time)

### Install maven

On Mac OS X:

- `brew install maven`
- make sure that the correct jdk is used (by default maven won't pick up a java install). I have this in my .bashrc:

```sh
latest_jdk=`cd /Library/Java/JavaVirtualMachines; ls | sort | tail -n 1`
export JAVA_HOME=/Library/Java/JavaVirtualMachines/$latest_jdk/Contents/Home
```

### set `$CLOJURE_FEATHER` and add it to `$PATH`

Like adding this to your .bashrc:

```sh
export CLOJURE_FEATHER=$HOME/clojure/clj-feather
export PATH=$CLOJURE_FEATHER:$PATH
```
