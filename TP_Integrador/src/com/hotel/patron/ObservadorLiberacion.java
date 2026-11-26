package com.hotel.patron;

import com.hotel.modelo.TipoHabitacion;

public interface ObservadorLiberacion {
    void actualizar(TipoHabitacion tipoLiberado, int costoPorNoche);
}