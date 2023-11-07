package br.com.rafaelDev.toDoList.Task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_Tasks")
public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID idUsuario;
    @Column(length = 30)
    private String titulo;
    private String descricao;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
