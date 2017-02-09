package ru.atom.model.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.collision.CollisionProfile;
import ru.atom.model.input.InputAction;
import ru.atom.util.V;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Pawn extends Actor {
    @JsonIgnore
    private boolean wantPlantBomb = false;
    @JsonIgnore
    private Collection<InputAction> inbox = new ArrayList<>();

    public static Pawn create(V position) {
        Pawn instance = new Pawn(position);
        instance.postConstruct();
        return instance;
    }

    private Pawn(V position) {
        setPosition(position);
        setVelocity(V.ZERO);
    }

    @Override
    public void tick(long time) {
        inbox.forEach(x -> x.act(this));
        super.tick(time);
        plantBomb();
        inbox.clear();
    }

    private void plantBomb() {
        if (!wantPlantBomb) {
            return;
        }

        // action
        wantPlantBomb = false;
    }

    public void addInput(InputAction inputAction) {
        inbox.add(inputAction);
    }

    public void setWantPlantBomb(boolean wantPlantBomb) {
        this.wantPlantBomb = wantPlantBomb;
    }

    @NotNull
    @Override
    public CollisionProfile getProfile() {
        return CollisionProfile.DYNAMIC;
    }
}