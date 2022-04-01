FROM gradle:7.4.1-jdk17 
## copia permissoes do gradle para o container
COPY --chown=gradle:gradle . .
WORKDIR .
RUN gradle build -x test --no-daemon
## pular step de teste
EXPOSE 8080
CMD ["gradle", "bootRun"]
