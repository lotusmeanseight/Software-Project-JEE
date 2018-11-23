HOW-TO
-convert xlsx to cvs
-bash öffnen
-"sed 's/^/( /' BLZ_20180903.csv > blz_insert_first.sql" eingeben
-"sed 's/$/) /' blz_insert_first.sql >blz_insert.sql" eingeben
-fertig!
