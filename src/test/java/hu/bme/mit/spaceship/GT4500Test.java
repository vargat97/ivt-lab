package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore torpedoStore;
  private TorpedoStore secondaryStore;

  @BeforeEach
  public void init(){
    torpedoStore = mock(TorpedoStore.class);
    secondaryStore = mock(TorpedoStore.class);
    this.ship = new GT4500(torpedoStore,secondaryStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(torpedoStore.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
   verify(torpedoStore,times(1)).fire(1);
  }
  @Test
  public void fireTorpedo_PrimaryFailure_SecondaryFire(){
    //Arrange
    when(torpedoStore.fire(1)).thenReturn(false);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(torpedoStore, times(1)).fire(1);
  }
  @Test
  public void fireTorpedo_PrimaryEmpty_SecondaryFire() {
    when(torpedoStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(false);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(secondaryStore,times(1)).fire(1);
  }
  @Test
  public void fireTorpedo_PrimariryFirstFire() {
    when(torpedoStore.fire(1)).thenReturn(true);
    when(torpedoStore.isEmpty()).thenReturn(false);

    ship.fireLaser(FiringMode.SINGLE);

    verifyNoInteractions(secondaryStore);
  }

  @Test
  public void fireToredo_Alternating() {
    when(torpedoStore.isEmpty()).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(torpedoStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    InOrder inOrder = inOrder(torpedoStore,secondaryStore);
    inOrder.verify(torpedoStore).fire(1);
    inOrder.verify(secondaryStore).fire(1);
  }
  @Test
  public void fireTorpedo_All_Failure() {
    when(torpedoStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.ALL);

    verify(torpedoStore,never()).fire(1);
    verify(secondaryStore,never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(torpedoStore.fire(1)).thenReturn(true);
    when(torpedoStore.isEmpty()).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(false);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(torpedoStore,times(1)).fire(1);
    verify(secondaryStore,times(1)).fire(1);
  }

}
