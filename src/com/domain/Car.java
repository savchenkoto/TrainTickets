package com.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cars", schema = "ticketsdb")
public class Car {
    private Integer id;
    private Integer number;
    private Train trainByTrainId;
    private Type typeByTypeId;
    private Collection<Seat> seatsById;

    public Car(Train trainByTrainId, Integer number, Type typeByTypeId) {
        this.trainByTrainId = trainByTrainId;
        this.number = number;
        this.typeByTypeId = typeByTypeId;
    }

    public Car() { }

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

        if (id != null ? !id.equals(car.id) : car.id != null) return false;
        if (number != null ? !number.equals(car.number) : car.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
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

    @OneToMany(mappedBy = "carByCarId", cascade = CascadeType.ALL)
    public Collection<Seat> getSeatsById() {
        return seatsById;
    }

    public void setSeatsById(Collection<Seat> seatsById) {
        this.seatsById = seatsById;
    }

    public void update(Car updatedCar) {
        this.setNumber(updatedCar.getNumber());
        this.setTrainByTrainId(updatedCar.getTrainByTrainId());
        this.setTypeByTypeId(updatedCar.getTypeByTypeId());
    }

    @Transient
    public StringProperty trainByTrainIdProperty() {
        return new SimpleStringProperty(trainByTrainId.getNumber());
    }

    @Transient
    public StringProperty typeByTypeIdProperty() {
        return new SimpleStringProperty(typeByTypeId.getName());
    }

    @Transient
    public StringProperty typeByTypedProperty() {
        return new SimpleStringProperty(typeByTypeId.getName());
    }
}
