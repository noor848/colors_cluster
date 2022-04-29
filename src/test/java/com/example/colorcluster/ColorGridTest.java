package com.example.colorcluster;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ColorGridTest {
    ColorGrid colorGrid;
    Random random;

    ColorGridTest() {
        random = mock(Random.class);
    }

    @BeforeEach
    void setUp() {
        colorGrid = new ColorGrid(random);
    }

    @Test
    void fillGrid() {
        when(random.nextInt(256)).thenReturn(5);
        colorGrid.fillGrid(5, 5);
        Color[][] grid = {{
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5)
        }, {
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5)
        }, {
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5)
        }, {
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5)
        }, {
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5),
                Color.rgb(5, 5, 5)
        }
        };
        assertArrayEquals(grid, colorGrid.colors);
    }

    @Test
    void insertColor() {
        colorGrid.insertColor(10, 10, 10);
        assertEquals(colorGrid.arrayList.get(0), Color.rgb(10, 10, 10));
    }
}