import React from 'react'
import { ResponsiveLine } from '@nivo/line'

const LineChart = () => {

    const data = [
        {
            id: "japan",
            color: "hsl(24, 70%, 50%)",
            data: [
                { x: "plane", y: 120 },
                { x: "helicopter", y: 80 },
                { x: "boat", y: 70 },
                { x: "train", y: 100 },
                { x: "subway", y: 90 },
                { x: "bus", y: 50 },
                { x: "car", y: 80 },
                { x: "moto", y: 60 },
                { x: "bicycle", y: 70 },
                { x: "horse", y: 90 },
                { x: "skateboard", y: 100 },
                { x: "others", y: 50 }
            ]
        },
        {
            id: "france",
            color: "hsl(180, 70%, 50%)",
            data: [
                { x: "plane", y: 100 },
                { x: "helicopter", y: 50 },
                { x: "boat", y: 60 },
                { x: "train", y: 90 },
                { x: "subway", y: 70 },
                { x: "bus", y: 40 },
                { x: "car", y: 60 },
                { x: "moto", y: 40 },
                { x: "bicycle", y: 50 },
                { x: "horse", y: 70 },
                { x: "skateboard", y: 90 },
                { x: "others", y: 30 }
            ]
        },
        {
            id: "us",
            color: "hsl(130, 70%, 50%)",
            data: [
                { x: "plane", y: 80 },
                { x: "helicopter", y: 60 },
                { x: "boat", y: 70 },
                { x: "train", y: 80 },
                { x: "subway", y: 60 },
                { x: "bus", y: 30 },
                { x: "car", y: 50 },
                { x: "moto", y: 30 },
                { x: "bicycle", y: 40 },
                { x: "horse", y: 60 },
                { x: "skateboard", y: 70 },
                { x: "others", y: 20 }
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