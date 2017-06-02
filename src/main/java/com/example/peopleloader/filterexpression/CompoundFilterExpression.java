package com.example.peopleloader.filterexpression;

import java.util.function.Predicate;

import com.example.peopleloader.argumentparser.arguments.operators.logical.LogicalBinaryOperator;
import com.example.peopleloader.model.Person;

public class CompoundFilterExpression implements FilterExpression {

	private final FilterExpression left;
	private final LogicalBinaryOperator op;
	private final FilterExpression right;

	public CompoundFilterExpression(FilterExpression left, LogicalBinaryOperator op, FilterExpression right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	@Override
	public Predicate<? super Person> getPredicate() {
		return null;
	}

	public FilterExpression getLeft() {
		return left;
	}

	public LogicalBinaryOperator getLogicalOperator() {
		return op;
	}

	public FilterExpression getRight() {
		return right;
	}

	@Override
	public String toString() {
		return left.toString() + " " + op.toString() + " " + right.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CompoundFilterExpression)) {
			return false;
		}
		CompoundFilterExpression other = (CompoundFilterExpression) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (op == null) {
			if (other.op != null) {
				return false;
			}
		} else if (!op.equals(other.op)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}
