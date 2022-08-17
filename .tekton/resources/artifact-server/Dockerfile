FROM harbor-cicsk8s.hursley.ibm.com/dockerhub/library/httpd:2.4
# Based on the apache web server image.

# Get the git repo name from the environment.
ARG GIT_REPO

# Get rid of index.html default file. We want to allow file browsing.
RUN rm -v /usr/local/apache2/htdocs/*

# Over-ride the configuration to enable file browsing
COPY ${GIT_REPO}/.tekton/resources/artifact-server/httpd.conf /usr/local/apache2/conf/httpd.conf

# Copy-in the binary contents we want to expose.
COPY built-artifacts/ /usr/local/apache2/htdocs/

# Just to make it obvious in the logs what we have just packaged.
RUN ls -R /usr/local/apache2/htdocs/



