package com.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cars", schema = "trainsdb")
public class Car {
    private Integer id;
    private Integer number;
    private Train trainByTrainId;
    private Type typeByTypeId;
    private Collection<Seat> seatsById;

    public Car(Train train, int number, Type type) {
        this.setTrainByTrainId(train);
        this.setNumber(number);
        this.setTypeByTypeId(type);
    }

    public Car() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) &&
                Objects.equals(number, car.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number);
    }

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    public Train getTrainByTrainId() {
        return trainByTrainId;
    }

    public void setTrainByTrainId(Train trainByTrainId) {
        this.trainByTrainId = trainByTrainId;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    public Type getTypeByTypeId() {
        return typeByTypeId;
    }

    public void setTypeByTypeId(Type typeByTypeId) {
        this.typeByTypeId = typeByTypeId;
    }

    @OneToMany(mappedBy = "carByCarId")
    public Collection<Seat> getSeatsById() {
        return seatsById;
    }

    public void setSeatsById(Collection<Seat> seatsById) {
        this.seatsById = seatsById;
    }

//    @Transient
//    public StringProperty trainByTrainIdProperty() {
//        return new SimpleStringProperty(trainByTrainId.getNumber());
//    }
//
//    @Transient
//    public StringProperty typeByTypeIdProperty() {
//        return new SimpleStringProperty(typeByTypeId.getName());
//    }

}
