FROM consul:latest
EXPOSE 8500

CMD ["agent", "-server", "-bind", "0.0.0.0", "-bootstrap"]
