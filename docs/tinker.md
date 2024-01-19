# Tinker 

- [asdf be here...](https://asdf-vm.com/ "asdf vm homepage")
- [Ruby be here...](https://www.ruby-lang.org/en/downloads/releases/ "Ruby releases")
- [Ruby installation example be here...](https://www.jdeen.com/blog/installing-ruby-using-asdf-in-macos/ "... one Ruby install example") by [Ziyan](https://www.jdeen.com/about/ziyan/profile)
- [GitHub Pages **_VERSIONS_** be here...](https://pages.github.com/versions/ "GitHub Pages supported component versions")
- https://jekyllrb.com/ goes Jekyll homepage
- [_**local**_ GitHub Pages with Jekyll](https://docs.github.com/en/pages/setting-up-a-github-pages-site-with-jekyll/testing-your-github-pages-site-locally-with-jekyll) <-- important

... have fun!

## TL:DR

Check for the old version of Ruby GHP wants:

```shell
ruby -v
# ruby 2.7.4p191 (2021-07-07 revision a21a3b7d23) [arm64-darwin23] -- is expected
# I ran on:
#   ruby 3.1.3p185 (2022-11-24 revision 1a6b16756e) [arm64-darwin23]
#   ... with additional gem:
#   bundle add webrick
```

```shell
 gem install bundler jekyll
```

```shell
bundle install
bundle exec jekyll serve
```

Optionally:

```shell
bundle add webrick
```