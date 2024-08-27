import React from 'react'
import { ResponsiveLine } from '@nivo/line'

const LineChart = () => {

    const data = [
        {
            id: "Region A - Revenue",
            color: "hsl(24, 70%, 50%)",
            data: [
                { x: "Q1", y: 120000 },
                { x: "Q2", y: 150000 },
                { x: "Q3", y: 130000 },
                { x: "Q4", y: 160000 }
            ]
        },
        {
            id: "Region A - Hotels",
            color: "hsl(24, 70%, 30%)",
            data: [
                { x: "Q1", y: 10 },
                { x: "Q2", y: 12 },
                { x: "Q3", y: 14 },
                { x: "Q4", y: 16 }
            ]
        },
        {
            id: "Region B - Revenue",
            color: "hsl(180, 70%, 50%)",
            data: [
                { x: "Q1", y: 90000 },
                { x: "Q2", y: 120000 },
                { x: "Q3", y: 110000 },
                { x: "Q4", y: 140000 }
            ]
        },
        {
            id: "Region B - Hotels",
            color: "hsl(180, 70%, 30%)",
            data: [
                { x: "Q1", y: 8 },
                { x: "Q2", y: 9 },
                { x: "Q3", y: 11 },
                { x: "Q4", y: 13 }
            ]
        },
        {
            id: "Region C - Revenue",
            color: "hsl(130, 70%, 50%)",
            data: [
                { x: "Q1", y: 80000 },
                { x: "Q2", y: 100000 },
                { x: "Q3", y: 95000 },
                { x: "Q4", y: 110000 }
            ]
        },
        {
            id: "Region C - Hotels",
            color: "hsl(130, 70%, 30%)",
            data: [
                { x: "Q1", y: 6 },
                { x: "Q2", y: 7 },
                { x: "Q3", y: 9 },
                { x: "Q4", y: 10 }
            ]
        }
    ];

  return (
    <ResponsiveLine
        data={data}
        margin={{ top: 50, right: 110, bottom: 50, left: 60 }}
        xScale={{ type: 'point' }}
        yScale={{
            type: 'linear',
            min: 'auto',
            max: 'auto',
            stacked: true,
            reverse: false
        }}
        yFormat=" >-.2f"
        axisTop={null}
        axisRight={null}
        axisBottom={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: 'transportation',
            legendOffset: 36,
            legendPosition: 'middle',
            truncateTickAt: 0
        }}
        axisLeft={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: 'count',
            legendOffset: -40,
            legendPosition: 'middle',
            truncateTickAt: 0
        }}
        pointSize={10}
        pointColor={{ theme: 'background' }}
        pointBorderWidth={2}
        pointBorderColor={{ from: 'serieColor' }}
        pointLabel="data.yFormatted"
        pointLabelYOffset={-12}
        enableTouchCrosshair={true}
        useMesh={true}
        legends={[
            {
                anchor: 'bottom-right',
                direction: 'column',
                justify: false,
                translateX: 100,
                translateY: 0,
                itemsSpacing: 0,
                itemDirection: 'left-to-right',
                itemWidth: 80,
                itemHeight: 20,
                itemOpacity: 0.75,
                symbolSize: 12,
                symbolShape: 'circle',
                symbolBorderColor: 'rgba(0, 0, 0, .5)',
                effects: [
                    {
                        on: 'hover',
                        style: {
                            itemBackground: 'rgba(0, 0, 0, .03)',
                            itemOpacity: 1
                        }
                    }
                ]
            }
        ]}
    />
  )
}

export default LineChart