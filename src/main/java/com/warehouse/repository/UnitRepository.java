package com.warehouse.repository;

import com.warehouse.model.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitRepository {

    private final List<Unit> units = new ArrayList<>();
    
    public List<Unit> findAll(){
        return new ArrayList<>(units);
    }

    public Optional<Unit> findById(String id){
        for(Unit u : units){
            if(u.getId() != null && u.getId().equals(id)) return Optional.of(u);
        }
        return Optional.empty();
    }

    public Optional<Unit> findBySerial(String serial){
        for(Unit u : units){
            if(u.getSerial() != null && u.getSerial().equals(serial)) return Optional.of(u);
        }
        return Optional.empty();
    }

    public Unit save(Unit unit){
        if(unit.getId() == null || unit.getId().isBlank()){
            unit.setId(java.util.UUID.randomUUID().toString());
        }
        units.add(unit);
        return unit;
    }

    public void update(Unit unit){
        for(int i = 0; i < units.size(); i++){
            if(units.get(i).getId().equals(unit.getId())){
                units.set(i, unit);
                return;
            }
        }
        throw new IllegalArgumentException("No unit with id " + unit.getId());
    }

    public void delete(String id){
        units.removeIf(u -> u.getId() != null && u.getId().equals(id));
    }

    public int count(){
        return units.size();
    }
}