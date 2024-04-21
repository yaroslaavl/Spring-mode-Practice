package com.dmdev.spring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity
public class Revision {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @RevisionNumber
     private Integer id;

     @RevisionTimestamp
     private Long timestamp;
}
