# Git Conflict

При слиянии ветки fix/conflict-demo в main возник конфликт в файле src/main/resources/application.properties, в строке со свойством server.port.

Обе ветки после общей точки расхождения (server.port=8080) независимо поменяли одну и ту же строку: в main порт стал 8081, а в fix/conflict-demo — 9090. Так как обе стороны изменили один и тот же фрагмент файла, Git не смог слить их автоматически и остановил merge с конфликтом.

После git merge --no-ff fix/conflict-demo файл выглядел так:

<<<<<<< HEAD
server.port=8081
=======
server.port=9090
>>>>>>> fix/conflict-demo

Конфликт исправлен вручную: маркеры <<<<<<<, ======= и >>>>>>> удалены, а вместо любого из двух вариантов выбрано новое значение — server.port=8090, чтобы не отдавать предпочтение ни одной из веток. После этого файл был добавлен через git add и зафиксирован коммитом fix: resolve merge conflict in application.properties, которым ветка fix/conflict-demo была окончательно слита в main.
