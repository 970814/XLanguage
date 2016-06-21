package moon.compile.util;

import moon.compile.function.FPointer;
import moon.compile.function.Function;
import moon.compile.function.Pointer;
import moon.compile.util.analyst.priority.Operator;
import moon.compile.util.variable.Data;
import moon.compile.util.variable.Var;
import moon.compile.util.variable.Vars;

public class Cal extends Operator {


    final Vars vars;

    public Cal(Vars vars) {
        this.vars = vars;
    }

    public Data cal(char operator, Data pop) throws Exception {
        if (pop instanceof Pointer) {
            Pointer pl = (Pointer) pop;
            if (((Pointer) pop).getPointer() instanceof Vars.Array && !((Vars.Array) ((Pointer) pop).getPointer()).isReference()) {
                pop = pl.getPointer();
            } else if (((Pointer) pop).getPointer() instanceof FPointer) {
                pop = pl.getPointer();
            }
        }
        if (isVarOperator(operator)) {
            switch (operator) {
                case Increment:
                    pop.setValue(pop.getValue() + 1);
                    return new Data(pop.getValue());
                case Decrement:
                    pop.setValue(pop.getValue() - 1);
                    return new Data(pop.getValue());

                default:
            }
        }
        if (operator == Get) {
            if (pop instanceof Var) {
                return ((Var) pop).remove();
            }
        }
        if (operator == FUNCTION) {
            return new Data(pop instanceof Function ? 1.0 : 0.0);
        }
        if (operator == SizeOf) {
            if (pop instanceof Vars.Array) {
                return new Data(((Vars.Array) pop).getArrayLength());
            } else {
                return new Data(0.0);
            }
        }
        if (operator == New) {
            //if success to instance, than return 1, else return 0.
            try {
                return newVar(pop);
            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                e.printStackTrace();
                Data data = vars.get(vars.getIndex(pop.getName()));
                if (data instanceof Vars.Array) {
                    return new Data(0.0);
                } else {
                    return data;
                }
            }
        }
        if (operator == InvokeFunction) {
            if (!(pop instanceof FPointer)) {
                try {
                    if (pop.getName() == null) {
                        throw new Exception("constant: " + pop.getValue());
                    } else {
                        throw new Exception("variable called: " + pop.getName());
                    }
                } catch (Exception e) {
                    throw new Exception("expected Function Pointer: behind operator: \'"
                            + convert(operator) + "\' " + e.getMessage() + " is not usage.");
                }
            }
//            JOptionPane.showMessageDialog(null, "got fun: " + ((FPointer) pop).getFunction(vars));
            return ((FPointer) pop).getFunction(vars);
        }
        return new Data(cal(operator, pop.getValue()));
    }

    private double cal(char operator, double operand) throws Exception {
        if (new String(new char[] { ArcSin, ArcCos, ArcTan, })
                .indexOf(operator) != -1) {
            if (operand > 1.0 || operand < -1.0) {
                throw new Exception(
                        "For the function: "
                                + convert(operator)
                                + ", the value: " + operand + " is undefined");
            }
        }
        if (operator == Ln || operator == Log) {
            if (operand <= 0.0) {
                throw new Exception(
                        "For the function: "
                                + convert(operator)
                                + ", the value: " + operand
                                + " must higher than 0.0");
            }
        }
        switch (operator) {
            case Percent:
                return operand / 100.0;
            case SquareRoot:
                if (operand < 0.0) {
                    throw new Exception("sqrt( " + operand + " ) can not be cal");
                }
                return Math.sqrt(operand);
            case Absolute:
                return Math.abs(operand);
            case Negative:
                return -1.0 * operand;
            case Factorial:
                if (operand > 20.0) {
                    throw new Exception("factorial calculation result overflows: "
                            + operand);
                }
                return Gamma.decimal_factorial(operand);
            case Sin:
                return Math.sin(operand);
            case Cos:
                return Math.cos(operand);
            case Tan:
                return Math.tan(operand);
            case ArcSin:
                return Math.asin(operand);
            case ArcCos:
                return Math.acos(operand);
            case ArcTan:
                return Math.atan(operand);
            case Ln:
                return Math.log(operand);
            case Log:
                return Math.log10(operand);
            case ToDegree:
                return Math.toDegrees(operand);
            case ToRadian:
                return Math.toRadians(operand);
            case Random:
                if (operand <= 0) {
                    throw new Exception("For function random: the argument must bigger than zero.");
                }
                return RANDOM.nextInt((int) operand);
            case Not:
                return operand == 0 ? 1 : 0;
            case Int:
                return (long) operand;
            default:
        }
        throw new Exception("undefined operator: \'" + convert(operator) + "\'; code: " + String.format("%#x", (int) operator));
    }


    public moon.compile.util.variable.Data cal(char operator, Data left, Data r) throws Exception {
        if (left instanceof Pointer) {
            Pointer pl = (Pointer) left;
            if (((Pointer) left).getPointer() instanceof Vars.Array && !((Vars.Array) ((Pointer) left).getPointer()).isReference()) {
                left = pl.getPointer();
            } else if (((Pointer) left).getPointer() instanceof FPointer) {
                left = pl.getPointer();
            }
//            JOptionPane.showMessageDialog(null, "left: " + left);
        }
        if (r instanceof Pointer) {
            Pointer pr = (Pointer) r;
            if (((Pointer) r).getPointer() instanceof Vars.Array && !((Vars.Array) ((Pointer) r).getPointer()).isReference()) {
                r = pr.getPointer();
            }else if (((Pointer) r).getPointer() instanceof FPointer) {
                r = pr.getPointer();
            }
//            JOptionPane.showMessageDialog(null, "right: " + r);
        }
        double right = r.getValue();
        if (isVarOperator(operator)) {
            switch (operator) {
                case Assign:
//                    JOptionPane.showMessageDialog(null, left + " : " + r);
                    if (left instanceof Vars.Array && !((Vars.Array) left).isReference()) {
                        if (r instanceof Vars.Array /*&& !((Vars.Array) r).isReference()*/) {
                            ((Vars.Array) left).setArray(((Vars.Array) r).getArray());
//                            JOptionPane.showMessageDialog(null, left + " : " + r);
                            return left;
                        }

//                        JOptionPane.showMessageDialog(null, left + " : " + r);
                    } else if (left instanceof FPointer) {
                        if (r instanceof FPointer) {
                            ((FPointer) left).reference(((FPointer) r).getPointer());

                        } else {
                            Function function = vars.getFunctionCalled(r.getName());
                            if (function != null) {
                                ((FPointer) left).reference(r.getName());
                            } else {
                                throw new Exception(left.getName() + " and " + (r.getName() == null ? "constant: " + r.getValue() : r.getName()) + " are incompatible types");
                            }
                        }

                        return left;
                    }
//                    System.err.println(3);
//                    ((Vars.Array) left).reference();

                    left.setValue(right);
                    if (left instanceof Vars.Array) {

//                        System.out.println(left);
//                        JOptionPane.showMessageDialog(null, left);
//                        JOptionPane.showMessageDialog(null, left.getValue());
                    }
                    break;
                case AddAssign:
                    left.setValue(left.getValue() + right);
                    break;
                case SubAssign:
                    left.setValue(left.getValue() - right);
                    break;
                case MulAssign:
                    left.setValue(left.getValue() * right);
                    break;
                case DivAssign:
                    left.setValue(left.getValue() / right);
                    break;
                case ModAssign:
                    left.setValue(left.getValue() % right);
                    break;
            }
            return new Data(left.getValue());
        }
        return new Data(cal(operator, left.getValue(), right));
    }

    private double cal(char operator, double left, double right)
            throws Exception {
        if (new String(new char[] { Divide, Modulo, }).indexOf(operator) != -1) {
            if (right == 0) {
                throw new Exception(
                        "For the operator: "
                                + convert(operator)
                                + ", zero can not be right operand");
            }
        }
        switch (operator) {
            case Add:
                return left + right;
            case Subtract:
                return left - right;
            case Multiply:
                return left * right;
            case Divide:
                return left / right;
            case Power:
                return Math.pow(left, right);
            case AnyThPowerRoot:
                return Math.pow(right, 1.0 / left);
            case Modulo:
                return left % right;
            case Bigger:
                return left > right ? 1.0 : 0.0;
            case Smaller:
                return left < right ? 1.0 : 0.0;
            case Same:
                return left == right ? 1.0 : 0.0;
            case Or:
                return left != 0.0 || right != 0.0 ? 1.0 : 0.0;
            case And:
                return left != 0.0 && right != 0.0 ? 1.0 : 0.0;
            case Comma:
                return right;
            case BiggerOrSame:
                return left >= right ? 1.0 : 0.0;
            case LowerOrSame:
                return left <= right ? 1.0 : 0.0;
            case NotEqual:
                return left != right ? 1.0 : 0.0;
            default:
        }
        throw new Exception("The operator: \'" + operator + "\' is undefined");
    }

    public Data newVar(moon.compile.util.variable.Data pop) throws Exception {
        String name = pop.getName();
        if (name == null) {
            throw new Exception("constant: " + pop.getValue() + " can't be instanced");
        }
        int index = vars.getIndex(name);
        if (index != -1 && !(vars.get(index) instanceof Function)) {
            throw new Exception("The variable called: \'" + name + "\' has already existed.");
        }
        if (pop.getType() == 1) {
           return newArray(name, pop.getArrayLength());
        }else {
//            JOptionPane.showMessageDialog(null, name);
            moon.compile.util.variable.Data data = new Data(name);
            vars.addFirst(data);
            return data;
        }
    }

    private moon.compile.util.variable.Data newArray(String name, int length) throws Exception {
//        vars.addAr(name, length);
        moon.compile.util.variable.Data data = new Vars.Array(name, length);
        vars.addFirst(data);
        ((Vars.Array) data).setNonReference();
        return data;
    }
}
