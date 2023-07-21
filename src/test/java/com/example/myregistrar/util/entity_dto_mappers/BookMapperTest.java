package com.example.myregistrar.util.entity_dto_mappers;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class BookMapperTest {
    @Mock
    BookMapper INSTANCE;
    @InjectMocks
    BookMapper bookMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme