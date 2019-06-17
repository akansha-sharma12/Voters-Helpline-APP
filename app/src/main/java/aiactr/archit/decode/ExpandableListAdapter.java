package aiactr.archit.decode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context _context;
    private List<String> listHeader;
    private HashMap<String, String> _childData;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, String> childData)
    {
        this._context = context;
        this.listHeader = listDataHeader;
        this._childData = childData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._childData.get(this.listHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_expanded, null);
        }

        ImageView img = convertView.findViewById(R.id.ps_img);
        TextView name = convertView.findViewById(R.id.ps_name);
        TextView acNoName = convertView.findViewById(R.id.ac_no_name);
        TextView partNo = convertView.findViewById(R.id.ps_part_no);
        TextView facility1 = convertView.findViewById(R.id.ps_facility_1);
        TextView facility2 = convertView.findViewById(R.id.ps_facility_2);
        TextView facility3 = convertView.findViewById(R.id.ps_facility_3);
        TextView facility4 = convertView.findViewById(R.id.ps_facility_4);
        TextView facility5 = convertView.findViewById(R.id.ps_facility_5);
        TextView facility6 = convertView.findViewById(R.id.ps_facility_6);
        TextView facility7 = convertView.findViewById(R.id.ps_facility_7);
        TextView facility8 = convertView.findViewById(R.id.ps_facility_8);
        TextView facility9 = convertView.findViewById(R.id.ps_facility_9);
        TextView facility10 = convertView.findViewById(R.id.ps_facility_10);
        TextView facility11 = convertView.findViewById(R.id.ps_facility_11);
        TextView facility12 = convertView.findViewById(R.id.ps_facility_12);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._childData.get(this.listHeader.get(groupPosition)).length();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_short, null);
        }

        TextView name = convertView.findViewById(R.id.ps_name);

        //set the name using getGroup(id)

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
