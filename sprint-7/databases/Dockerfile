FROM postgres:10.6
COPY *.sql /docker-entrypoint-initdb.d/
RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
ENV LANG de_DE.utf8
ENV POSTGRES_PASSWORD sep
# ENV PGPORT 5433
# ENV DB_PORT 5433
# ENV POSTGRES_USER sep # if not set than default user postgres is used