package com.ekrishibazaar.model;

public class BlockModel {

    private String block_name;
    private String block_id;

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    @Override
    public String toString() {
        return block_name;
    }

}
