package com.dillo.pathfinding.stevebot.core.pathfinding.actions;

import com.dillo.pathfinding.stevebot.core.data.blocks.BlockCache;
import com.dillo.pathfinding.stevebot.core.data.modification.Modification;
import com.dillo.pathfinding.stevebot.core.misc.Direction;
import com.dillo.pathfinding.stevebot.core.pathfinding.actions.playeractions.Action;
import com.dillo.pathfinding.stevebot.core.pathfinding.nodes.Node;

public interface ActionFactory {
    /**
     * Check if a valid action can be created starting from the given node
     *
     * @param node the node
     * @return the {@link Result} of the check
     */
    Result check(Node node);

    /**
     * Creates an action starting from the given node
     *
     * @param node   the node
     * @param result the result of a previous valid check of the same node in the same step.
     * @return the created action
     */
    Action createAction(Node node, Result result);

    /**
     * @return the rough direction of the action this factory will produce
     */
    Direction getDirection();

    /**
     * @return the type of action produced by this factory
     */
    Class<? extends Action> producesAction();

    enum ResultType {
        /**
         * The action is a valid action.
         */
        VALID,

        /**
         * The action is NOT a valid action.
         */
        INVALID,

        /**
         * The action is in an unloaded area and not valid.
         */
        UNLOADED,
    }

    class Result {

        private static final Result invalid = new Result();
        private static final Result unloaded = new Result();
        private static final Result valid = new Result();
        public ResultType type = ResultType.INVALID;
        public double estimatedCost = ActionCosts.get().COST_INFINITE;
        public Node to = null;
        public Direction direction = Direction.NONE;
        public Modification[] modifications;

        /**
         * @return a new {@link Result} of the type {@link ResultType#INVALID}.
         */
        public static Result invalid() {
            if (invalid.type != ResultType.INVALID) {
                invalid.type = ResultType.INVALID;
            }
            return invalid;
        }

        /**
         * @return a new {@link Result} of the type {@link ResultType#UNLOADED}.
         */
        public static Result unloaded() {
            if (unloaded.type != ResultType.UNLOADED) {
                unloaded.type = ResultType.UNLOADED;
            }
            return unloaded;
        }

        /**
         * @param direction the direction of the action
         * @param to        the target node of the action
         * @param cost      the cost of the action
         * @return a new {@link Result} of the type {@link ResultType#VALID}.
         */
        public static Result valid(Direction direction, Node to, double cost) {
            valid.type = ResultType.VALID;
            valid.estimatedCost = cost;
            valid.to = to;
            valid.direction = direction;
            return valid;
        }

        /**
         * @param direction     the direction of the action
         * @param to            the target node of the action
         * @param cost          the cost of the action
         * @param modifications the list of {@link BlockCache}s
         * @return a new {@link Result} of the type {@link ResultType#VALID}.
         */
        public static Result valid(Direction direction, Node to, double cost, Modification[] modifications) {
            Result result = valid(direction, to, cost);
            result.modifications = modifications;
            return result;
        }
    }
}
